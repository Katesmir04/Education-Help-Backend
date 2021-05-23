package com.kate.eduhelp.controller;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kate.eduhelp.init.FirebaseInitialize;
import com.kate.eduhelp.models.*;
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

    @PostMapping("/pushNewUser")
    public void pushNewUser(@RequestBody User user) throws ExecutionException, InterruptedException {

        System.out.println("got " + user);

        CollectionReference cr = firebaseInstance.getFirestore().collection("users");

        cr.document(user.id).set(user);

    }

    @GetMapping("/addBonusesToUser")
    @ResponseBody
    public void addBonusesToUser(@RequestParam(name = "id") String id, @RequestParam(name = "number") int number) throws ExecutionException, InterruptedException {
        CollectionReference cr = firebaseInstance.getFirestore().collection("users");

        DocumentReference dr = firebaseInstance.getFirestore().collection("users").document(id);

        Long oldValue = (Long) dr.get().get().get("totalBonuses");

        dr.update("totalBonuses", oldValue + number);


    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public User getUserInfo(@RequestParam(name = "id") String id) throws ExecutionException, InterruptedException {

        Query q = firebaseInstance.getFirestore().collection("users").whereEqualTo("id", id);

        ApiFuture<QuerySnapshot> qq = q.get();

        List<User> list = new ArrayList<>();

        for (QueryDocumentSnapshot doc : qq.get().getDocuments()) {
            list.add(doc.toObject(User.class));
        }

        return list.get(0);
    }


    @PostMapping("/addPassedQuize")
    public void addPassedQuize(@RequestBody QuizeItem quizeItem) throws ExecutionException, InterruptedException {

        DocumentReference dr = firebaseInstance.getFirestore().collection("users").document(quizeItem.userId);

        dr.collection("passed").document(quizeItem.quize.id).set(quizeItem);

    }

    @GetMapping("/getPassedQuizes")
    public List<QuizeItem> getPassedQuizes(@RequestParam(name = "id") String id) throws ExecutionException, InterruptedException {


        List<QuizeItem> list = new ArrayList<>();

        CollectionReference cr = firebaseInstance.getFirestore().collection("users").document(id).collection("passed");

        ApiFuture<QuerySnapshot> q = cr.get();

        for (DocumentSnapshot doc : q.get().getDocuments()) {
            list.add(doc.toObject(QuizeItem.class));
        }

        return list;
    }

    @PostMapping("/addFavorite")
    public void addFavorite(@RequestBody Topic topic, @RequestParam(name = "id") String id) throws ExecutionException, InterruptedException {

        DocumentReference dr = firebaseInstance.getFirestore().collection("users").document(id);

        dr.collection("favorites").document(topic.id).set(topic);

    }

    @PostMapping("/removeFavorite")
    public void removeFavorite(@RequestBody Topic topic, @RequestParam(name = "id") String id) throws ExecutionException, InterruptedException {

        DocumentReference dr = firebaseInstance.getFirestore().collection("users").document(id);

        dr.collection("favorites").document(topic.id).delete();

    }

    @GetMapping("/getFavorites")
    public List<Topic> getFavorites(@RequestParam(name = "id") String id) throws ExecutionException, InterruptedException {


        List<Topic> list = new ArrayList<>();

        CollectionReference cr = firebaseInstance.getFirestore().collection("users").document(id).collection("favorites");

        ApiFuture<QuerySnapshot> q = cr.get();

        for (DocumentSnapshot doc : q.get().getDocuments()) {
            list.add(doc.toObject(Topic.class));
        }

        return list;
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() throws ExecutionException, InterruptedException {


        List<User> list = new ArrayList<>();

        CollectionReference cr = firebaseInstance.getFirestore().collection("users");

        ApiFuture<QuerySnapshot> q = cr.get();

        for (DocumentSnapshot doc : q.get().getDocuments()) {
            list.add(doc.toObject(User.class));
        }

        return list;
    }

}
