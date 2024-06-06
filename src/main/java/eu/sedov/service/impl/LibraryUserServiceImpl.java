package eu.sedov.service.impl;

import eu.sedov.model.*;
import eu.sedov.repository.impl.BookRepositoryImpl;
import eu.sedov.repository.impl.ReviewRepositoryImpl;
import eu.sedov.repository.impl.UserRepositoryImpl;
import eu.sedov.service.LibraryUserService;

import java.util.List;

public class LibraryUserServiceImpl implements LibraryUserService {
    private UserRepositoryImpl userRepository;
    private BookRepositoryImpl bookRepository;
    private ReviewRepositoryImpl reviewRepository;

    @Override
    public User getById(Integer id) {
        User user = userRepository.get(id);
        if (user == null)
            return null;

        List<Book> books = bookRepository.getAll();
        List<Review> review = reviewRepository.getAll();
        var userReviews = userRepository.getUsersReview().stream()
                .filter(x -> x.userId().equals(user.getId()))
                .map(UserReview::reviewId)
                .toList();
        user.setReviewList(review.stream()
                .filter(x -> userReviews.contains(x.getId()))
                .toList());

        var userBooks = userRepository.getUsersBooks().stream()
                .filter(x -> x.userId().equals(user.getId()))
                .map(UserBook::bookId)
                .toList();
        user.setBookList(books.stream()
                .filter(x -> userBooks.contains(x.getId()))
                .toList());

        user.getReviewList().forEach(x -> x.setUser(user));
        user.getBookList().forEach(x -> x.getUserList().add(user));
        return user;
    }
    @Override
    public int insert(User user) {
        return userRepository.insert(user);
    }
    @Override
    public int delete(Integer id) {
        return userRepository.delete(id);
    }
    @Override
    public int update(User user) {
        return userRepository.update(user);
    }
}
