package dev.ivy.wallet.wallet.service;


import dev.ivy.wallet.wallet.vo.Feedback;
import dev.ivy.wallet.wallet.vo.Response;

public interface IFeedbackService {

    /**
     * <h2>Create feedback</h2>
     * @param feedback {@link Feedback}
     * @return {@link Response}
     */
    Response createFeedback(Feedback feedback);

    /**
     * <h2>Get user feedback</h2>
     * @param userId User ID
     * @return {@link Response}
     */
    Response getFeedback(Long userId);
}
