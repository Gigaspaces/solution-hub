package org.springframework.data.xap.repository.query;

import com.j_spaces.core.client.SQLQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anna_Babich.
 */
public class PartTreeXapRepositoryQuery extends XapRepositoryQuery{

    private final XapQueryMethod method;
    private final PartTree tree;
    private final SpaceClient space;

    public PartTreeXapRepositoryQuery(XapQueryMethod method, SpaceClient space){
        super(method);

        Class<?> domainClass = method.getEntityInformation().getJavaType();

        this.tree = new PartTree(method.getName(), domainClass);
        this.method = method;
        this.space = space;
    }

    @Override
    public Object execute(Object[] parameters) {
        ParametersParameterAccessor parameterAccessor = new ParametersParameterAccessor(method.getParameters(), parameters);

        String className = method.getPersistentEntity().getName();

        String query = new XapQueryCreator(tree, parameterAccessor).createQuery();

        SQLQuery sqlQuery = new SQLQuery(className, query);

        sqlQuery.setParameters(prepareStringParameters(parameters));

        return space.readMultiple(sqlQuery);
    }



    private Object[] prepareStringParameters(Object[] parameters) {
        Iterator<Part> partsIterator = tree.getParts().iterator();
        List<Object> stringParameters = new ArrayList<Object>(parameters.length);

        for (Object parameter : parameters) {
            if (parameter == null || parameter instanceof Sort) {
                stringParameters.add(parameter);
            }
            else {
                switch (partsIterator.next().getType()) {
                    case CONTAINING:
                        stringParameters.add(String.format("%%%s%%", parameter.toString()));
                        break;
                    case STARTING_WITH:
                        stringParameters.add(String.format("%s%%", parameter.toString()));
                        break;
                    case ENDING_WITH:
                        stringParameters.add(String.format("%%%s", parameter.toString()));
                        break;
                    default:
                        stringParameters.add(parameter);
                }
            }
        }

        return stringParameters.toArray();
    }

}
