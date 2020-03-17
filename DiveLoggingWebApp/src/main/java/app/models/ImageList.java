package app.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class ImageList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToMany(mappedBy = "associatedList")
	private List<DBFile> images = new ArrayList<DBFile>();
	
	// Constructors
	public ImageList() {
	}
	
	// Id -----------------------------------------
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public List<DBFile> getImages() {
		return images;
	}

	public void setImages(List<DBFile> images) {
		this.images = images;
	}

}
