package com.company.jmixpm.screen.customsearch;

import com.company.jmixpm.entity.SearchResultItem;
import io.jmix.core.Metadata;
import io.jmix.search.searching.EntitySearcher;
import io.jmix.search.searching.FieldHit;
import io.jmix.search.searching.SearchContext;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@UiController("CustomSearchScreen")
@UiDescriptor("custom-search-screen.xml")
public class CustomSearchScreen extends Screen {

    @Autowired
    private EntitySearcher entitySearcher;
    @Autowired
    private Metadata metadata;
    @Autowired
    private CollectionContainer<SearchResultItem> searchResultItemsDc;

    @Subscribe("searchField")
    public void onSearchFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        String value = event.getValue();
        if (Strings.isNullOrEmpty(value)) {
            searchResultItemsDc.getMutableItems().clear();
            return;
        }

        SearchContext context = new SearchContext(value).setSize(100);

        List<SearchResultItem> resultItems = entitySearcher.search(context)
                .getAllEntries().stream().map(resultEntry -> {
                    String fields = resultEntry.getFieldHits().stream()
                            .map(FieldHit::getFieldName)
                            .collect(Collectors.joining(", "));
                    return createSearchResultItem(resultEntry.getEntityName(), resultEntry.getInstanceName(),
                            resultEntry.getDocId(), fields);
                })
                .collect(Collectors.toList());

        searchResultItemsDc.setItems(resultItems);
    }

    private SearchResultItem createSearchResultItem(String entityName, String instanceName,
                                                    String instanceId,
                                                    String fields) {
        SearchResultItem item = metadata.create(SearchResultItem.class);
        item.setEntityName(entityName);
        item.setInstanceName(instanceName);
        item.setInstanceId(instanceId);
        item.setFields(fields);
        return item;
    }

}