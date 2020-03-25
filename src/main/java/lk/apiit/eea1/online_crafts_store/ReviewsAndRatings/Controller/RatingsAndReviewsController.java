package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Controller;

import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.DTO.RatingsDTO;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.DTO.ReviewDTO;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Service.RatingsAndReviewService;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ratingsreviews")
@Controller
public class RatingsAndReviewsController {

    @Autowired
    RatingsAndReviewService ratingsAndReviewService;

    @RequestMapping(value = "/addrating",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRating(@RequestHeader(value = "Authorization") String token, @RequestBody RatingsDTO dto) throws Exception {
        Utils.checkToken(token);
        ratingsAndReviewService.addRating(dto);
        return ResponseEntity.ok("Rating Successfuly added.");
    }

    @RequestMapping(value = "/addreview",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewDTO dto) throws Exception {
        Utils.checkToken(token);
        ratingsAndReviewService.addReview(dto);
        return ResponseEntity.ok("Review Successfuly added.");
    }

    @RequestMapping(value = "/craftreview/{id}",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reviewsForCreatorsCraft(@RequestHeader(value = "Authorization") String token, @PathVariable(name="id") long id) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(ratingsAndReviewService.getReviewsForItemsOfCreator(id));
    }

}
