package com.kate.eduhelp.controller;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kate.eduhelp.init.FirebaseInitialize;
import com.kate.eduhelp.models.Quize;
import com.kate.eduhelp.models.Test;
import com.kate.eduhelp.models.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("data")
public class MainController {

    @Autowired
    private FirebaseInitialize firebaseInstance;

    @GetMapping("/allTopics")
    public List<Topic> allTopics() throws ExecutionException, InterruptedException {

        List<Topic> list = new ArrayList<>();

        CollectionReference cr = firebaseInstance.getFirestore().collection("topics");

        ApiFuture<QuerySnapshot> q = cr.get();

        for (DocumentSnapshot doc : q.get().getDocuments()) {
            list.add(doc.toObject(Topic.class));
        }

        return list;
    }

    @PostMapping("/addTopic")
    public String addTopic(@RequestBody Topic topic) {
        CollectionReference cr = firebaseInstance.getFirestore().collection("topics");

        cr.document(String.valueOf(topic.id)).set(topic);

        return topic.id;
    }

    @GetMapping("/getTestsByTopicId")
    @ResponseBody
    public List<Test> getTestsByTopicId(@RequestParam(name = "id") String id) throws ExecutionException, InterruptedException {

        Query q = firebaseInstance.getFirestore().collection("tests").whereEqualTo("topic", id);

        ApiFuture<QuerySnapshot> qq = q.get();

        List<Test> list = new ArrayList<>();

        for (QueryDocumentSnapshot doc : qq.get().getDocuments()) {
            list.add(doc.toObject(Test.class));
        }

        return list;
    }

    @GetMapping("/getAllQuizes")
    public List<Quize> getAllQuizes() throws ExecutionException, InterruptedException {

        List<Quize> list = new ArrayList<>();

        CollectionReference cr = firebaseInstance.getFirestore().collection("quizes");

        ApiFuture<QuerySnapshot> q = cr.get();

        for (DocumentSnapshot doc : q.get().getDocuments()) {
            list.add(doc.toObject(Quize.class));
        }

        return list;
    }
}
