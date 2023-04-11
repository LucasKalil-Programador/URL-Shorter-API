package br.com.api.kalil_studio.URL_Shorter.services;

import br.com.api.kalil_studio.URL_Shorter.models.URLModel;
import br.com.api.kalil_studio.URL_Shorter.repositories.URLRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.api.kalil_studio.URL_Shorter.utils.validURL.validURL;

@Service
public class URLService {

    @Autowired
    final URLRepository urlRepository;

    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public URLModel save(URLModel urlModel) {
        return urlRepository.save(urlModel);
    }

    public List<URLModel> findAll() {
        return urlRepository.findAll();
    }

    public Optional<URLModel> find(long id) {
        return urlRepository.findById(id);
    }

    public boolean existsByOriginalURL(String originalURL) {
        return urlRepository.existsByOriginalURL(originalURL) == 1;
    }

    public Optional<URLModel> findByOriginalURL(String originalURL) {
        return urlRepository.findFirstByOriginalURL(originalURL);
    }

    @Transactional
    public Integer cleanExpiredRegisters() {
        return urlRepository.deleteExpiredRegisters();
    }
}
