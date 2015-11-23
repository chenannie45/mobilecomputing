package edu.asu.mc29.mywardrobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.asu.mc29.mywardrobe.commons.Events;
import edu.asu.mc29.mywardrobe.commons.EventsGetter;

/**
 * Created by rrshah9 on 11/22/15.
 */
public class EventsListViewAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Events> events;


    public EventsListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        events = EventsGetter.readCalendarEvent(context);
    }


    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.events_item_layout, parent, false);
            //imageView = (ImageView) convertView.findViewById(R.id.grid_image);
        }
        TextView event_name = (TextView)convertView.findViewById(R.id.event_name);
        TextView event_description = (TextView)convertView.findViewById(R.id.event_description);
        event_name.setText(position+1 + " " + events.get(position).getName());
        event_description.setText(events.get(position).getDescription());
        return convertView;
    }
}
