package fi.academy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item {

    private String itemName;
    private String itemDescription;
    private String itemEvent;
    private boolean heavy;

    public Item(String itemName, String itemDescription, boolean heavy) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.heavy = heavy;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemEvent() {
        return itemEvent;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("itemName='").append(itemName).append('\'');
        sb.append(", itemDescription='").append(itemDescription).append('\'');
        sb.append(", Can be lifted=").append(heavy);
        sb.append('}');
        return sb.toString();
    }
}