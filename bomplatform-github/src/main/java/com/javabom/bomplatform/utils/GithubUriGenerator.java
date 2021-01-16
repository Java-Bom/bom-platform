package com.javabom.bomplatform.utils;

public class GithubUriGenerator {


    private static final String GITHUB_API_URI_PREFIX = "https://api.github.com/";


    public static String generateCreatePullRequestUri(String owner, String repo) {
        return GITHUB_API_URI_PREFIX + "repos/" + owner + "/" + repo + "/pulls";
    }

    public static String generateGetRepoRequestUri(final String org) {
        return GITHUB_API_URI_PREFIX + "orgs/" + org +"/repos";
    }

    public static String generateGetBranchesRequestUri(final String owner, final String repo) {
        return "https://api.github.com/repos/" + owner +"/" + repo + "/branches";
    }

    public static String generateRequestReviewRequestUri(final String owner, final String repo,
                                                         final Integer pullNumber) {
        return GITHUB_API_URI_PREFIX + "repos/" + owner + "/" + repo + "/pulls/" + pullNumber + "/requested_reviewers";

    }

    public static String generateCreateBranchRequestUri(final String owner, final String repo) {
        return GITHUB_API_URI_PREFIX + "repos/" + owner + "/" + repo + "/git" + "/refs";
    }

    public static String generateGetPullRequests(final String owner, final String repo) {
        return GITHUB_API_URI_PREFIX + "repos/" + owner + "/" + repo + "/pulls";
    }
}
