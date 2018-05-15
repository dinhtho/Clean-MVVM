package com.hieupham.cleanarchitecture.feature.tasklist;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import com.hieupham.cleanarchitecture.data.model.Task;
import com.hieupham.cleanarchitecture.data.source.TaskRepository;
import com.hieupham.cleanarchitecture.utils.livedata.Resource;
import java.util.List;

/**
 * Created by hieupham on 5/14/18.
 */

public class TaskListViewModel extends ViewModel {

    private TaskRepository taskRepo;
    private final MutableLiveData<String> uid = new MutableLiveData<>();
    private LiveData<Resource<List<Task>>> liveTasks =
            Transformations.switchMap(uid, new Function<String, LiveData<Resource<List<Task>>>>() {
                @Override
                public LiveData<Resource<List<Task>>> apply(String uid) {
                    return taskRepo.getTasksByOwner(uid);
                }
            });

    TaskListViewModel(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    void init(String uid) {
        this.uid.setValue(uid);
    }

    @Override
    LiveData<Resource<List<Task>>> tasks() {
        return liveTasks;
    }
}