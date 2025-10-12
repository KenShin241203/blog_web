package com.blogweb.project.blogweb_be.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogweb.project.blogweb_be.dto.request.tag.TagCreationRequest;
import com.blogweb.project.blogweb_be.dto.respone.ApiResponse;
import com.blogweb.project.blogweb_be.dto.respone.TagResponse;
import com.blogweb.project.blogweb_be.service.TagService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {

    TagService tagService;

    @GetMapping
    ApiResponse<List<TagResponse>> getAllTag() {
        return ApiResponse.<List<TagResponse>>builder()
                .result(tagService.getAllTag())
                .build();
    }

    @PostMapping
    ApiResponse<TagResponse> createTag(@RequestBody TagCreationRequest request) {
        return ApiResponse.<TagResponse>builder()
                .result(tagService.createTag(request))
                .build();
    }

    @DeleteMapping("{tagName}")
    ApiResponse<Object> deleteTag(@PathVariable("tagName") String tagName) {
        tagService.deleteTag(tagName);
        return ApiResponse.builder()
                .message("Delete with tag name: " + tagName)
                .build();
    }
}
