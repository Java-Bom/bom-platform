package com.javabom.bomplatform.github.model;

import lombok.Data;
@Data
public class CreateBranchParams {

    private String owner;

    private String repo;

    private String branchName;

    private CreateBranchParams(String owner, String repo, String branchName) {
        this.owner = owner;
        this.repo = repo;
        this.branchName = branchName;
    }

    public static CreateBranchParams of(String owner, String repo, String branchName) {
        return new CreateBranchParams(owner, repo, branchName);
    }


    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

}
