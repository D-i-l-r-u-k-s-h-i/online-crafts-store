package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Service;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.CraftCreatorRepository;
import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import lk.apiit.eea1.online_crafts_store.Cart.Repository.CartRepository;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftCreatorCraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftCreatorItemRepository;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftItemRepository;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.DTO.RatingsDTO;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.DTO.ReviewDTO;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity.RateCraftCreator;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity.ReviewCraftItem;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Repository.RateCraftCreatorRepository;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Repository.ReviewCraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingsAndReviewService {

    @Autowired
    ReviewCraftItemRepository reviewCraftItemRepository;

    @Autowired
    RateCraftCreatorRepository rateCraftCreatorRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CraftCreatorRepository craftCreatorRepository;

    @Autowired
    CraftItemRepository craftItemRepository;

    @Autowired
    CraftCreatorItemRepository craftCreatorItemRepository;

    public void addRating(RatingsDTO ratingsDTO){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        CraftCreator craftCreator=craftCreatorRepository.getByCreatorId(ratingsDTO.getCreatorId());

        RateCraftCreator rateCraftCreatorPrevious=rateCraftCreatorRepository.getByCraftCreatorAndUser(craftCreator,cart.getUser());

        if(rateCraftCreatorPrevious!=null){
            rateCraftCreatorPrevious.setRating(ratingsDTO.getRating());
        }
        else{
            RateCraftCreator rateCraftCreator=new RateCraftCreator();
            rateCraftCreator.setRating(ratingsDTO.getRating());
            rateCraftCreator.setUser(cart.getUser());
            rateCraftCreator.setCraftCreator(craftCreator);

            rateCraftCreatorRepository.save(rateCraftCreator);
        }

        calculateOverallRating(craftCreator);
    }

    public void addReview(ReviewDTO reviewDTO){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        CraftItem craftItem=craftItemRepository.findByCraftId(reviewDTO.getCraftId());

        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");

        ReviewCraftItem reviewCraftItem=new ReviewCraftItem();
        reviewCraftItem.setCraftItem(craftItem);
        reviewCraftItem.setReviewDescription(reviewDTO.getReviewDescription());
        reviewCraftItem.setUser(cart.getUser());
        reviewCraftItem.setDate(formatter.format(new Date()));

        reviewCraftItemRepository.save(reviewCraftItem);
    }

    public List<ReviewDTO> getReviewsForItemsOfCreator(long id){
        CraftCreator craftCreator;
        if(id == 0) {
            UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            craftCreator = craftCreatorRepository.findByUser_Id(userSession.getId());
        }
        else{
            craftCreator=craftCreatorRepository.getByCreatorId(id);
        }


        List<CraftCreatorCraftItem> creatorCraftItems=craftCreatorItemRepository.getAllByCraftCreator(craftCreator);

        List<CraftItem> craftItemList=creatorCraftItems.stream().map(CraftCreatorCraftItem::getCraftItem).collect(Collectors.toList());

        List<ReviewCraftItem> reviewCraftItemList=reviewCraftItemRepository.getAllByCraftItemIn(craftItemList);

        return Utils.mapAll(reviewCraftItemList,ReviewDTO.class);
    }

    //gets called whenever someone rates
    public double calculateOverallRating(CraftCreator craftCreator){

        int totalRatings=rateCraftCreatorRepository.countAllByCraftCreator(craftCreator);

        int count1stars=rateCraftCreatorRepository.countAllByRatingAndCraftCreator(1,craftCreator);
        int count2stars=rateCraftCreatorRepository.countAllByRatingAndCraftCreator(2,craftCreator);
        int count3stars=rateCraftCreatorRepository.countAllByRatingAndCraftCreator(3,craftCreator);
        int count4stars=rateCraftCreatorRepository.countAllByRatingAndCraftCreator(4,craftCreator);
        int count5stars=rateCraftCreatorRepository.countAllByRatingAndCraftCreator(5,craftCreator);

        double weighted_mean=(count1stars+2*count2stars+3*count3stars+4*count4stars+5*count5stars)/totalRatings;
        craftCreator.setOverallRating(weighted_mean);
        craftCreatorRepository.save(craftCreator);

        return weighted_mean;
    }
}
