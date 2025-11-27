package com.ulbra.achadoseperdidos;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitária para gerenciar operações do Firebase
 * Centraliza lógica de autenticação e banco de dados
 */
public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private static final String COLLECTION_USUARIOS = "usuarios";
    private static final String COLLECTION_ITENS = "achados";

    private static FirebaseAuth auth;
    private static FirebaseFirestore db;

    // ==================== AUTH ====================

    public static FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
            Log.d(TAG, "Firebase Auth inicializado");
        }
        return auth;
    }

    public static FirebaseFirestore getFirestore() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
            Log.d(TAG, "Firestore inicializado");
        }
        return db;
    }

    public static boolean isUserLoggedIn() {
        return getAuth().getCurrentUser() != null;
    }

    public static FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    public static void fazerLogout() {
        getAuth().signOut();
        Log.d(TAG, "Logout realizado");
    }

    // ==================== USUÁRIOS ====================

    public static void cadastrarUsuario(String email, String senha, String nome,
                                        OnSuccessListener successListener,
                                        OnFailureListener failureListener) {
        getAuth().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = getAuth().getCurrentUser();
                        if (user != null) {
                            salvarDadosUsuario(user.getUid(), nome, email, successListener, failureListener);
                        }
                    } else {
                        String erro = task.getException() != null ?
                                task.getException().getMessage() : "Erro desconhecido";
                        failureListener.onFailure(erro);
                    }
                });
    }

    private static void salvarDadosUsuario(String uid, String nome, String email,
                                           OnSuccessListener successListener,
                                           OnFailureListener failureListener) {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nome", nome);
        usuario.put("email", email);
        usuario.put("dataCadastro", System.currentTimeMillis());
        usuario.put("ativo", true);

        getFirestore().collection(COLLECTION_USUARIOS)
                .document(uid)
                .set(usuario)
                .addOnSuccessListener(aVoid -> successListener.onSuccess())
                .addOnFailureListener(e -> failureListener.onFailure(e.getMessage()));
    }

    // ==================== ITENS ACHADOS ====================

    /**
     * Salva um novo item perdido no Firestore
     */
    public static void salvarItem(String nomeItem, String localizacao, String dataEncontrada,
                                  String tipo, String imagemUrl,
                                  OnSuccessListener successListener,
                                  OnFailureListener failureListener) {

        Map<String, Object> item = new HashMap<>();
        item.put("nomeItem", nomeItem);
        item.put("localizacao", localizacao);
        item.put("dataEncontrada", dataEncontrada);
        item.put("tipo", tipo);
        item.put("imagemUrl", imagemUrl);
        item.put("usuarioId", getCurrentUser() != null ? getCurrentUser().getUid() : null);

        getFirestore().collection(COLLECTION_ITENS)
                .add(item)
                .addOnSuccessListener(documentReference -> successListener.onSuccess())
                .addOnFailureListener(e -> failureListener.onFailure(e.getMessage()));
    }

    /**
     * Lista todos os itens perdidos
     */
    public static void listarItens(OnItensListener listener) {
        getFirestore().collection(COLLECTION_ITENS)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Map<String, Object>> itens = new java.util.ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        itens.add(doc.getData());
                    }
                    listener.onItensReceived(itens);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erro ao listar itens: " + e.getMessage());
                    listener.onItensReceived(null);
                });
    }

    // ==================== INTERFACES DE CALLBACK ====================

    public interface OnSuccessListener {
        void onSuccess();
    }

    public interface OnFailureListener {
        void onFailure(String erro);
    }

    public interface OnDataListener {
        void onDataReceived(Map<String, Object> data);
    }

    public interface OnItensListener {
        void onItensReceived(List<Map<String, Object>> itens);
    }
}
