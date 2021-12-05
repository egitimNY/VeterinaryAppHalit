package com.halit.veterinaryapphalit.RestApi;

import com.halit.veterinaryapphalit.Models.AnswerModel;
import com.halit.veterinaryapphalit.Models.AskQuestionModel;
import com.halit.veterinaryapphalit.Models.DeleteAnswerModel;
import com.halit.veterinaryapphalit.Models.LoginModel;
import com.halit.veterinaryapphalit.Models.PetModel;
import com.halit.veterinaryapphalit.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<RegisterPojo> kayitOl(String mail , String kadi, String parola)
    {
        Call<RegisterPojo> x = getRestApi().registerUser(mail,kadi,parola);
        return  x ;
    }

    public Call<LoginModel> girisYap(String mail , String parola)
    {
        Call<LoginModel> x = getRestApi().loginUser(mail,parola);
        return  x ;
    }

    public Call<List<PetModel>> getPets(String id)
    {
        Call<List<PetModel>> x = getRestApi().getPets(id);
        return  x ;
    }

    public Call<AskQuestionModel> soruSor(String id , String soru)
    {
        Call<AskQuestionModel> x = getRestApi().soruSor(id,soru);
        return  x ;
    }

    public Call<List<AnswerModel>> getAnswer(String id) {
        Call<List<AnswerModel>> x = getRestApi().getAnswer(id);
        return  x ;
    }

    public Call<DeleteAnswerModel> deleteAnswer(String cevap , String soru)
    {
        Call<DeleteAnswerModel> x = getRestApi().deleteAnswer(cevap,soru);
        return  x ;
    }

}
