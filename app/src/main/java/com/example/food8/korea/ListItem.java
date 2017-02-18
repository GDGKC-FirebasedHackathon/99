package com.example.food8.korea;

/**
 * Created by hch on 16. 11. 14.
 */
public class ListItem {
    private String txtTitle;
    private String addr;
    private String zipcode;
    private String tel;
    private String contid;
    private String conttype;
    private String url_image;

    public ListItem(String txt, String addr, String zip){
        this.txtTitle = txt;
        this.addr = addr;
        this.zipcode = zip;
        this.tel = "";
        this.contid="";
        this.conttype="";
        this.url_image="";
    }
    public ListItem(String txt, String addr, String zip, String tel, String contid, String conttype, String url_image){
        this.txtTitle = txt;
        this.addr = addr;
        this.zipcode = zip;
        this.tel = tel;
        this.contid = contid;
        this.conttype = conttype;
        this.url_image = url_image;
    }

    public String getimage() {
        return url_image;
    }

    public void setimage(String url_image) {
        this.url_image = url_image;
    }


    public String getcontid() {
        return contid;
    }

    public void setcontid(String contid) {
        this.contid = contid;
    }

    public String getconttype() {
        return conttype;
    }

    public void setconttype(String conttype) {
        this.conttype = conttype;
    }

    public String gettxtTitle() {
        return txtTitle;
    }

    public void settxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public String getaddr() {
        return addr;
    }

    public void setaddr(String addr) {
        this.addr = addr;
    }

    public String getzipecode() {
        return zipcode;
    }

    public void setzipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String gettel() {
        return tel;
    }

    public void settel(String tel) {
        this.tel = tel;
    }


    @Override
    public String toString() {
        return "[ txtTitle=" + txtTitle + ", addr=" + addr  + " , zipecode" + zipcode + "tel" + tel +  "]";
    }
}
