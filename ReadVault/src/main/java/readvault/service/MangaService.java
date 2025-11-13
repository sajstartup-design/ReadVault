package readvault.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import readvault.object.MMMData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MangaService {

    private Firestore db() {
        return FirestoreClient.getFirestore();
    }

    // ✅ CREATE or UPDATE (save)
    public String saveManga(MMMData data)
            throws ExecutionException, InterruptedException {

        ApiFuture<WriteResult> future =
                db().collection("mmm")
                    .document(data.getTitle())   
                    .set(data);

        future.get(); // wait
        return data.getTitle();
    }

    // ✅ UPDATE only (if exists)
    public String updateManga(String title, MMMData updated)
            throws ExecutionException, InterruptedException {

        DocumentReference docRef = db().collection("mmm").document(title);

        if (!docRef.get().get().exists()) {
            return null; // not found
        }

        ApiFuture<WriteResult> future = docRef.set(updated);
        future.get();
        return title;
    }

    // ✅ DELETE
    public boolean deleteManga(String title)
            throws ExecutionException, InterruptedException {

        DocumentReference docRef = db().collection("mmm").document(title);

        if (!docRef.get().get().exists()) {
            return false; // cannot delete missing
        }

        ApiFuture<WriteResult> future = docRef.delete();
        future.get();
        return true;
    }

    // ✅ GET SPECIFIC
    public MMMData getManga(String title)
            throws ExecutionException, InterruptedException {

        DocumentSnapshot snapshot =
                db().collection("mmm")
                    .document(title)
                    .get()
                    .get();

        if (!snapshot.exists()) return null;

        return snapshot.toObject(MMMData.class);
    }

    // ✅ GET ALL
    public List<MMMData> getAllManga()
            throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future =
                db().collection("mmm").get();

        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        List<MMMData> result = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            result.add(doc.toObject(MMMData.class));
        }

        return result;
    }

    // ✅ GET ALL EXCEPT ONE (exclude by title)
    public List<MMMData> getAllExcept(String excludedTitle)
            throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future =
                db().collection("mmm").get();

        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        List<MMMData> result = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            if (!doc.getId().equals(excludedTitle)) {
                result.add(doc.toObject(MMMData.class));
            }
        }

        return result;
    }
}

