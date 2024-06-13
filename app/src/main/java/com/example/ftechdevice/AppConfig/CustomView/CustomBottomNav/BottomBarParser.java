package com.example.ftechdevice.AppConfig.CustomView.CustomBottomNav;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.ftechdevice.Common.Constants.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BottomBarParser {

    private final Context context;
    private final XmlResourceParser parser;

    public BottomBarParser(Context context, int res) {
        this.context = context;
        this.parser = context.getResources().getXml(res);
    }

    public List<BottomBarItem> parse() {
        List<BottomBarItem> items = new ArrayList<>();
        int eventType;

        try {
            while ((eventType = parser.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG && Constants.ITEM_TAG.equals(parser.getName())) {
                    items.add(getTabConfig(parser));
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }

        return items;
    }

    private BottomBarItem getTabConfig(XmlResourceParser parser) {
        int attributeCount = parser.getAttributeCount();
        String itemText = null;
        Drawable itemDrawable = null;

        for (int i = 0; i < attributeCount; i++) {
            String attributeName = parser.getAttributeName(i);
            if (Constants.ICON_ATTRIBUTE.equals(attributeName)) {
                itemDrawable = ContextCompat.getDrawable(context, parser.getAttributeResourceValue(i, 0));
            } else if (Constants.TITLE_ATTRIBUTE.equals(attributeName)) {
                try {
                    itemText = context.getString(parser.getAttributeResourceValue(i, 0));
                } catch (Exception e) {
                    itemText = parser.getAttributeValue(i);
                }
            }
        }

        return new BottomBarItem(itemText, itemDrawable);
    }
}
