package org.miejski.service.group;

import org.miejski.domain.group.GroupDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.toList;

@Component
public class GroupDefinitionsProvider {

    private static final String GROUP_DEFINITION_SPLITTER = "=";

    @Value("${group.definitions.filename}")
    private String path;

    private List<GroupDefinition> groups;

    @PostConstruct
    public void retrieveGroupsDefinitionsFromConfig() {
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            List<String> definitions = readAllLines(Paths.get(classPathResource.getURI()));
            groups = definitions.stream().map(x -> x.split(GROUP_DEFINITION_SPLITTER))
                    .map(x -> GroupDefinition.of(x[0], Integer.valueOf(x[1])))
                    .collect(toList());
        } catch (IOException error) {
            error.printStackTrace();
            throw new IllegalStateException("Error parsing groups definitions!", error);
        }
    }

    public List<GroupDefinition> getGroups() {
        return groups;
    }
}
