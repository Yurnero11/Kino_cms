package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Halls;
import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.NewsRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.NewsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
public class NewsEditController {
    private final NewsService newsService;
    private final GalleryRepository galleryRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final NewsRepository newsRepository;


    public NewsEditController(NewsService newsService, GalleryRepository galleryRepository, SeoBlocksRepository seoBlocksRepository, NewsRepository newsRepository) {
        this.newsService = newsService;
        this.galleryRepository = galleryRepository;
        this.seoBlocksRepository = seoBlocksRepository;
        this.newsRepository = newsRepository;
    }


    @GetMapping("/news/{news_id}/edit")
    public String editCinema(@PathVariable(value = "news_id") long id, Model model) {
        if (!newsRepository.existsById(id)) {
            return "redirect:/news";
        }

        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            return "redirect:/news";
        }

        News news = newsOptional.get();

        // Добавление объекта cinema в модель
        model.addAttribute("new_s", news);

        return "news/news-edit";
    }

    @PostMapping("/news/{news_id}/edit")
    public String newsEdit(
            @PathVariable(value = "news_id") long id,
            @RequestParam("news_name") String news_name,
            @RequestParam("status") String status,
            @RequestParam("news_description") String news_description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publication_date,
            @RequestParam("main_image_path") MultipartFile main_image_path,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,
            @RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("description_seo") String descriptionSeo)
    {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            return "redirect:/cinemas";
        }

        News news = newsOptional.get();
        news.setNews_title(news_name);
        news.setNews_description(news_description);
        news.setDate_publication(publication_date);
        news.setStatus(status);
        news.setMain_image_path(saveImage(main_image_path, news.getMain_image_path()));
        news.getGallery().setImagePath1(saveImage(upload1, news.getGallery().getImagePath1()));
        news.getGallery().setImagePath2(saveImage(upload2, news.getGallery().getImagePath2()));
        news.getGallery().setImagePath3(saveImage(upload3, news.getGallery().getImagePath3()));
        news.getGallery().setImagePath4(saveImage(upload4, news.getGallery().getImagePath4()));
        news.getGallery().setImagePath5(saveImage(upload5, news.getGallery().getImagePath5()));
        news.getSeoBlocks().setUrl(url);
        news.getSeoBlocks().setTitle(title);
        news.getSeoBlocks().setKeywords(keywords);
        news.getSeoBlocks().setDescription(descriptionSeo);

        if (main_image_path != null && !main_image_path.isEmpty()) {
            news.setMain_image_path(saveImage(main_image_path, news.getMain_image_path()));
        } else {
            news.setMain_image_path(news.getMain_image_path()); // Сохраняем старое значение
        }

        newsRepository.save(news);

        return "redirect:/news";
    }

    @PostMapping("/news/{news_id}/remove")
    public String removeNews(@PathVariable("news_id") long news_id){
        Optional<News> optionalNews = newsRepository.findById(news_id);
        if (optionalNews.isEmpty()) {
            // Обработка ошибки - кинотеатр не найден
            return "redirect:/news";
        }

        newsRepository.delete(optionalNews.get());

        return "redirect:/news";
    }


    private final String uploadDir = "upload";

    private String saveImage(MultipartFile file, String currentImagePath) {
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(originalFileName);
                String uniqueFileName = generateUniqueFileName(fileExtension);

                Path filePath = Paths.get(uploadDir, uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return currentImagePath; // Возвращаем старое значение
        }
    }

    private String generateUniqueFileName(String fileExtension) {
        String timeStamp = UUID.randomUUID().toString();
        return timeStamp + "." + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
