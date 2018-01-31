package com.su.nuttawut.coffeepuppy.RealmDB;

import com.su.nuttawut.coffeepuppy.Data.CartDetail;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Pokemon on 1/30/2018.
 */

public class realmOrder {
    private final Realm realm;
    private static realmOrder instance;

    public realmOrder() {
        realm = Realm.getDefaultInstance();
    }

    public static realmOrder getInstance(){
        if (instance == null)
            instance = new realmOrder();
        return instance;

    }
    public Realm getRealm(){
        return realm;
    }

    public void clearAll(){
        realm.beginTransaction();
        realm.delete(CartDetail.class);
        realm.commitTransaction();
    }
    public void deleteOrders(int id){
        realm.beginTransaction();
        realm.where(CartDetail.class)
                .equalTo("order_id",id)
                .findFirst()
                .deleteFromRealm();
        realm.commitTransaction();
    }
    public RealmResults<CartDetail> getOrders(){
        return realm.where(CartDetail.class).findAll();
    }
    public void addOrders(CartDetail cartDetail){
        realm.beginTransaction();
        realm.insert(cartDetail);
        realm.commitTransaction();
    }
    public int nextid(){
        Number cartDetail = realm.where(CartDetail.class).max("order_id");
        if (cartDetail == null){
            return 10001;
        } else {
            return cartDetail.intValue() + 1;
        }
    }
}
