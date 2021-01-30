package com.javabom.bomplatform.github.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GithubRepoResponse {

    private Long id;

    private String name;

    private String branchesUri;



}
