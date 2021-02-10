package com.kate.eduhelp.controller;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.kate.eduhelp.init.FirebaseInitialize;
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
}
