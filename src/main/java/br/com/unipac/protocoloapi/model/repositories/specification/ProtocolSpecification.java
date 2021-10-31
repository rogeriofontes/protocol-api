package br.com.unipac.protocoloapi.model.repositories.specification;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProtocolSpecification implements Specification<Protocol> {

    private Protocol filter;

    public ProtocolSpecification(Protocol filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Protocol> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.disjunction();

        if (filter.getName() != null) {
            predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
        }

        return predicate;
    }
}
