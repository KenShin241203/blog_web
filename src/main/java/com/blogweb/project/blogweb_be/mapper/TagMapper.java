package com.blogweb.project.blogweb_be.mapper;

import org.mapstruct.Mapper;

import com.blogweb.project.blogweb_be.dto.request.tag.TagCreationRequest;
import com.blogweb.project.blogweb_be.dto.respone.TagResponse;
import com.blogweb.project.blogweb_be.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag maptoTag(TagCreationRequest request);

    TagResponse mapToTagResponse(Tag tag);
}
