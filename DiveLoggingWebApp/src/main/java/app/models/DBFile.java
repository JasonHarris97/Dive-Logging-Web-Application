package app.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "files")
public class DBFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_image_id")
    private User fileOwner;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dive_id")
    private Dive associatedDive;

    private String fileName;

    private String fileType;
    
    private String fileUse;

    @Lob
    private byte[] data;

    public DBFile() {

    }

    public DBFile(String fileName, String fileType, byte[] data) {
        this.setFileName(fileName);
        this.setFileType(fileType);
        this.data = data;
    }
    
 // Id -----------------------------------------
 	public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileUse() {
		return fileUse;
	}

	public void setFileUse(String fileUse) {
		this.fileUse = fileUse;
	}

	public User getFileOwner() {
		return fileOwner;
	}

	public void setFileOwner(User fileOwner) {
		this.fileOwner = fileOwner;
	}

	public Dive getAssociatedDive() {
		return associatedDive;
	}

	public void setAssociatedDive(Dive associatedDive) {
		this.associatedDive = associatedDive;
	}    
}