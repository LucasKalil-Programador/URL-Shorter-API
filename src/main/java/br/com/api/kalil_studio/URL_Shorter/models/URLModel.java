package br.com.api.kalil_studio.URL_Shorter.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "URL")
public class URLModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 4096)
    private String originalURL;

    // Timing control
    @Column(nullable = false)
    private LocalDateTime registrationDateTime;
    @Column(nullable = false)
    private LocalDateTime expirationDateTime;

    // Access control
    @Column(nullable = false)
    private Integer maxAccess;
    @Column(nullable = false)
    private Integer accessCount;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Integer getMaxAccess() {
        return maxAccess;
    }

    public void setMaxAccess(Integer maxAccess) {
        this.maxAccess = maxAccess;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }
}
