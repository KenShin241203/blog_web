package com.blogweb.project.blogweb_be.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.tag.TagCreationRequest;
import com.blogweb.project.blogweb_be.dto.respone.TagResponse;
import com.blogweb.project.blogweb_be.entity.Tag;
import com.blogweb.project.blogweb_be.exception.AppException;
import com.blogweb.project.blogweb_be.exception.ErrorCode;
import com.blogweb.project.blogweb_be.mapper.TagMapper;
import com.blogweb.project.blogweb_be.repository.TagRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagService {

    TagRepository tagRepository;
    TagMapper tagMapper;

    public TagResponse createTag(TagCreationRequest request) {
        boolean tagExisted = tagRepository.existsById(request.getName());
        if (tagExisted) {
            throw new AppException(ErrorCode.Tag_EXISTED);
        }
        Tag tag = tagMapper.maptoTag(request);
        return tagMapper.mapToTagResponse(tagRepository.save(tag));
    }

    public List<TagResponse> getAllTag() {
        return tagRepository.findAll().stream().map(tagMapper::mapToTagResponse).toList();
    }

    public void deleteTag(String name) {
        tagRepository.deleteById(name);
    }
}
