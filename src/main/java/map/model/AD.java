package map.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AD implements Comparable<AD>, Serializable {
    private long id;
    private String title;
    private String text;
    private int price;
    private Date date;
    private Category category;
    private User outher;
    private SimpleDateFormat sdf = new SimpleDateFormat(" dd-MM-yyyy hh:mm:ss");

    @Override
    public int compareTo(AD ad) {
        return title.compareTo(ad.getTitle());
    }
}
