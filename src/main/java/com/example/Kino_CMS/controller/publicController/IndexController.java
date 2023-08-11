package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import com.example.Kino_CMS.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private AboutCinemaServiceImpl aboutCinemaService;
    @Autowired
    private PageServiceImpl pageService;
    @Autowired
    private MainPageServiceImpl mainPageService;
    @Autowired
    private CafeBarServiceImpl cafeBarService;
    @Autowired
    private VipHallServiceImpl vipHallService;
    @Autowired
    private AdvertisingServiceImpl advertisingService;
    @Autowired
    private KidsRoomServiceImpl kidsRoomService;
    @Autowired
    private ContactForTableServiceImpl contactForTableService;
    @Autowired
    private MovieServiceImpl movieServiceImpl;
    @Autowired
    private MainBannerServiceImpl mainBannersService;
    @Autowired
    private UserRepository userRepository;











    @GetMapping("/home")
    public String index(Model model, HttpServletRequest request){
        MainBanners latestMainBanner = mainBannersService.getLatestMainBanner();
        model.addAttribute("mainBanner", latestMainBanner);

        Iterable<Movies> movies = movieServiceImpl.getAllMovies();
        model.addAttribute("movies", movies);

        Iterable<AboutCinema> aboutCinemas = aboutCinemaService.getAllAboutCinema();
        model.addAttribute("about_cinema", aboutCinemas);

        Iterable<Pages> pages = pageService.getAllPages();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        Iterable<MainPage> mainPages = mainPageService.getAllMainPages();
        model.addAttribute("mainPages", mainPages);

        Iterable<CafeBar> cafeBars = cafeBarService.getAllCafeBars();
        model.addAttribute("cafe_bar", cafeBars);

        Iterable<VipHall> vipHalls = vipHallService.getAllVipHals();
        model.addAttribute("vip_halls", vipHalls);

        Iterable<Advertising> advertising = advertisingService.getAllAdvertising();
        model.addAttribute("advertising", advertising);

        Iterable<KidsRoom> kidsRooms = kidsRoomService.getAllKidsRoom();
        model.addAttribute("kidsroom", kidsRooms);

        Iterable<Contact_for_table> contactForTables = contactForTableService.getAllContacts();
        model.addAttribute("cinemaContactsForTable", contactForTables);

        return "public/index-page/index";
    }

    @GetMapping("/header")
    public String header(Model model, HttpServletRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);


            // Добавляем id пользователя в модель
            User user = userRepository.findByUsername(username);
            if (user != null) {
                Integer userId = user.getId();
                model.addAttribute("id", userId);
            } else {
                // Обработка случая, когда пользователя с таким именем нет в базе данных
            }
        }
        Iterable<AboutCinema> aboutCinemas = aboutCinemaService.getAllAboutCinema();
        model.addAttribute("about_cinema", aboutCinemas);

        Iterable<Pages> pages = pageService.getAllPages();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        Iterable<MainPage> mainPages = mainPageService.getAllMainPages();
        model.addAttribute("mainPages", mainPages);

        Iterable<CafeBar> cafeBars = cafeBarService.getAllCafeBars();
        model.addAttribute("cafe_bar", cafeBars);

        Iterable<VipHall> vipHalls = vipHallService.getAllVipHals();
        model.addAttribute("vip_halls", vipHalls);

        Iterable<Advertising> advertising = advertisingService.getAllAdvertising();
        model.addAttribute("advertising", advertising);

        Iterable<KidsRoom> kidsRooms = kidsRoomService.getAllKidsRoom();
        model.addAttribute("kidsroom", kidsRooms);

        Iterable<Contact_for_table> contactForTables = contactForTableService.getAllContacts();
        model.addAttribute("cinemaContactsForTable", contactForTables);

        return "public/content/header";
    }

    @GetMapping("/footer")
    public String footer(Model model, HttpServletRequest request){
        Iterable<AboutCinema> aboutCinemas = aboutCinemaService.getAllAboutCinema();
        model.addAttribute("about_cinema", aboutCinemas);

        Iterable<Pages> pages = pageService.getAllPages();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        Iterable<MainPage> mainPages = mainPageService.getAllMainPages();
        model.addAttribute("mainPages", mainPages);

        Iterable<CafeBar> cafeBars = cafeBarService.getAllCafeBars();
        model.addAttribute("cafe_bar", cafeBars);

        Iterable<VipHall> vipHalls = vipHallService.getAllVipHals();
        model.addAttribute("vip_halls", vipHalls);

        Iterable<Advertising> advertising = advertisingService.getAllAdvertising();
        model.addAttribute("advertising", advertising);

        Iterable<KidsRoom> kidsRooms = kidsRoomService.getAllKidsRoom();
        model.addAttribute("kidsroom", kidsRooms);

        Iterable<Contact_for_table> contactForTables = contactForTableService.getAllContacts();
        model.addAttribute("cinemaContactsForTable", contactForTables);

        return "public/content/footer";
    }
}
