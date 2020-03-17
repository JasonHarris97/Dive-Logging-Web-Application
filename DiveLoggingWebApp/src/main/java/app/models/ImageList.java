package app.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "imageList")
public class ImageList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne(mappedBy = "imageList")
	private Dive dive;
	
	@OneToMany(mappedBy = "associatedList")
	private List<DBFile> images = new ArrayList<DBFile>();
	
	// Constructors
	public ImageList() {
	}
	
	// Constructors
	public ImageList(Dive dive) {
		this.dive = dive;
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

	public Dive getDive() {
		return dive;
	}

	public void setDive(Dive dive) {
		this.dive = dive;
	}

}
