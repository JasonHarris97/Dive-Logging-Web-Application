package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.models.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	DBFile findById(Long id);
}