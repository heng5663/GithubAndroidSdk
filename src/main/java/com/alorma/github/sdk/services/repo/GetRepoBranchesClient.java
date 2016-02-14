package com.alorma.github.sdk.services.repo;

import com.alorma.github.sdk.bean.dto.response.Branch;
import com.alorma.github.sdk.bean.info.RepoInfo;
import com.alorma.github.sdk.services.client.BaseInfiniteCallback;
import com.alorma.github.sdk.services.client.GithubClient;
import com.alorma.github.sdk.services.client.GithubListClient;
import com.alorma.gitskarios.core.client.BaseListClient;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;

public class GetRepoBranchesClient extends GithubClient<List<Branch>> {

    private RepoInfo repoInfo;

    public GetRepoBranchesClient(RepoInfo repoInfo) {
        this.repoInfo = repoInfo;
    }

    @Override
    protected Observable<List<Branch>> getApiObservable(RestAdapter restAdapter) {
        return Observable.create(new BaseInfiniteCallback<List<Branch>>() {
            @Override
            public void execute() {
                restAdapter.create(RepoService.class).branches(repoInfo.owner, repoInfo.name, this);
            }

            @Override
            protected void executePaginated(int nextPage) {
                restAdapter.create(RepoService.class).branches(repoInfo.owner, repoInfo.name, nextPage, this);
            }
        });
    }
}
