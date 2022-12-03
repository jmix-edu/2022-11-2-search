package com.company.jmixpm.search;

import com.company.jmixpm.entity.User;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = User.class)
public interface UserIndexDefinition {

    @AutoMappedField(includeProperties = {"*"}, excludeProperties = {"password"})
    void mapping();
}
