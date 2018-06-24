package us.martink.bl.todo.repsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.martink.bl.todo.model.TodoItemSearch;
import us.martink.bl.todo.model.TodoItemView;
import us.martink.model.todo.TodoItem;
import us.martink.model.todo.TodoItem_;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * Created by tadas.
 */
@Repository
@Transactional
public class TodoRepoImpl extends SimpleJpaRepository<TodoItem, Long> implements TodoRepo {

    private EntityManager em;

    public TodoRepoImpl(EntityManager entityManager, EntityManager em) {
        super(TodoItem.class, entityManager);
        this.em = em;
    }

    @Override
    public Page<TodoItemView> findAllBySearch(TodoItemSearch todoItemSearch) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TodoItemView> criteria = criteriaBuilder.createQuery(TodoItemView.class);
        Root<TodoItem> root = criteria.from(TodoItem.class);

        Specification<TodoItem> specification = (r, cq, cb) -> cb.equal(r.get(TodoItem_.archived), todoItemSearch.isArchived());
        criteria.where(specification.toPredicate(root, criteria, criteriaBuilder));

        criteria.multiselect(
                root.get(TodoItem_.id),
                root.get(TodoItem_.created),
                root.get(TodoItem_.content),
                root.get(TodoItem_.archived)
        );
        criteria.where(criteriaBuilder.equal(root.get(TodoItem_.archived), todoItemSearch.isArchived()));
        criteria.orderBy(criteriaBuilder.desc(root.get(TodoItem_.created)));

        TypedQuery<TodoItemView> typedQuery = em.createQuery(criteria);
        typedQuery.setFirstResult(todoItemSearch.getActivePage() * todoItemSearch.getPageSize() - todoItemSearch.getPageSize());
        typedQuery.setMaxResults(todoItemSearch.getPageSize());

        PageRequest pageRequest = PageRequest.of(todoItemSearch.getActivePage(), todoItemSearch.getPageSize());
        return new PageImpl<>(typedQuery.getResultList(), pageRequest, count(specification));
    }

    @Override
    public void markArchived(Long id, Boolean archived) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<TodoItem> criteria = criteriaBuilder.createCriteriaUpdate(TodoItem.class);
        Root<TodoItem> root = criteria.from(TodoItem.class);

        criteria.where(criteriaBuilder.equal(root.get(TodoItem_.id), id));
        criteria.set(root.get(TodoItem_.archived), archived);
        em.createQuery(criteria).executeUpdate();
    }
}
