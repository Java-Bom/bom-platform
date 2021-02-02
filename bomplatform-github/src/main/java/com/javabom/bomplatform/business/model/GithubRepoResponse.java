package com.javabom.bomplatform.business.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GithubRepoResponse {

    private Long id;

    private String name;

    private String branchesUri;



}
