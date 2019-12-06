package com.example.projetotcc10.Controle;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.Modelo.Turma;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ManterGrades extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private TextView linkSelectFile;
    private EditText anoTurma;
    private Button buttonUparGrade;
    private Spinner spnCursos;
    private Spinner spnTurmas;
    private String nomeCurso;
    private Uri pdfUri;
    private List<Curso> cursos;
    private List<Turma> turmas;

    private Curso curso;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private FirebaseStorage storage;
    private FirebaseFirestore database;
    private final static String TAG  = "Firelog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_grades);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Enviar grades");

        cursos = new ArrayList<>();
        turmas = new ArrayList<>();

        storage = FirebaseStorage.getInstance();
        database = FirebaseFirestore.getInstance();

        linkSelectFile = findViewById(R.id.textSelecionarArquivo);
        buttonUparGrade = findViewById(R.id.uparGrade);
        spnCursos = findViewById(R.id.spinnerCursoGrades);
        spnTurmas = findViewById(R.id.spinnerTurma);

        spnCursos.setOnItemSelectedListener(this);

        carregarSpinnerCurso();

        linkSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(ManterGrades.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                  selectPdf();
                }
                else {
                    ActivityCompat.requestPermissions(ManterGrades.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }

        });

        //   final ArrayAdapter<Curso> spinnerCursos = new ArrayAdapter<Curso>(this, android.R.layout.simple_spinner_item, cursos);
        //  spinnerCursos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  cursos.setAdapter(spinnerCursos);
        buttonUparGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");


                if (pdfUri!= null) {
                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(pdfUri));

                    mUploadTask = fileReference.putFile(pdfUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {


                                          Curso curso = (Curso) spnCursos.getSelectedItem();
                                          nomeCurso = curso.getCurso();
                                          Turma turma = (Turma) spnTurmas.getSelectedItem();
                                          turma.setCurso(curso.getCurso());

                                            Toast.makeText(getBaseContext(), "Upload successful", Toast.LENGTH_LONG).show();

                                            turma.setUrlGrade(uri.toString());

                                            FirebaseFirestore.getInstance().collection("cursos").document(curso.getId())
                                                    .collection("turmas").document(turma.getId()).set(turma);
                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                   // mProgressBar.setProgress((int) progress);
                                }
                            });
                } else {
                    Toast.makeText(getBaseContext(), "No file selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton voltar = findViewById(R.id.buttonActionVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Admin.class);
                startActivity(intent);
            }
        });
    }
    public void carregarSpinnerCurso() {

        FirebaseFirestore.getInstance().collection("cursos")
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cursos.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String nomeCurso = document.getString("curso");


                                Curso u = new Curso();
                                u.setId(document.getId());
                                u.setCurso(nomeCurso);

                                cursos.add(u);
                                Log.d(TAG, nomeCurso);

                            }

                            final ArrayAdapter <Curso> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, cursos);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnCursos.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }
    public void carregarSpinnerTurma() {

        curso = (Curso) spnCursos.getSelectedItem();

        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    turmas.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String anoTurma = document.getString("ano");
                        String semestreTurma = document.getString("semestre");

                        Turma u = new Turma();
                        u.setId(document.getId());
                        u.setAno(anoTurma);
                        u.setSemestre(semestreTurma);
                        turmas.add(u);

                    }

                    final ArrayAdapter<Turma> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, turmas);
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnTurmas.setAdapter(adaptador);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

    }

    private String getFileExtension(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
        else {
            Toast.makeText(this, "please provide permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione o arquivo PDF"), 86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==86 && resultCode==RESULT_OK && data!=null){

            pdfUri = data.getData();

        }
        else{
            Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Admin.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        carregarSpinnerTurma();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
