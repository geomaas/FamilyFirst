package com.james.entities;

import javax.persistence.*;

/**
 * Created by user on 7/21/16.
 */
@Entity
@Table(name = "tips")
public class ProTip {
    @GeneratedValue
    @Id
    int tipsId;

    @Column(nullable = false)
    String tip;

    public ProTip() {
    }

    public ProTip(int tipsId, String tip) {
        this.tipsId = tipsId;
        this.tip = tip;
    }

    public ProTip(String tip) {
        this.tip = tip;
    }

    public int getTipsId() {
        return tipsId;
    }

    public void setTipsId(int tipsId) {
        this.tipsId = tipsId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "ProTip{" +
                "tip='" + tip + '\'' +
                '}';
    }
}
