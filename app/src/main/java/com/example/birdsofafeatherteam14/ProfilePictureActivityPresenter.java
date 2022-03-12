package com.example.birdsofafeatherteam14;

public class ProfilePictureActivityPresenter {
    private IPicture pic;
    private View view;

    ProfilePictureActivityPresenter(IPicture pic, View view) {
        this.view = view;
        this.updatePicture(pic);
    }

    public void updatePicture(IPicture pic) {
        this.pic = pic;
        view.setPicture(this.pic);
    }

    public IPicture getPicture() {
        return this.pic;
    }

    public interface View {
        void setPicture(IPicture pic);
    }
}
