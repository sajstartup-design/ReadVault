package readvault.object;

import java.util.List;

import lombok.Data;

@Data
public class MMMData {
	private String title;
    private List<String> genre;
    private int rating;
}
