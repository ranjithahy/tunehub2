package com.demo.example.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.demo.example.entities.Song;
import com.demo.example.entities.Users;
import com.demo.example.services.SongService;
import com.demo.example.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

    @Autowired
    private UsersService userService;

    @Autowired
    private SongService songService;

    // ✅ Step 1: Show Razorpay payment page
    @GetMapping("/pay")
    public String pay() {
        return "pay"; // pay.html view
    }

    // ✅ Step 2: Create Razorpay order and mark user as premium
    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(HttpSession session) {
        int amount = 5000; // ₹50
        Order order = null;

        try {
            RazorpayClient razorpay = new RazorpayClient(
                "rzp_test_XqtKtnPMD9aBmg",
                "HfofyBk9hRUFp4XJ4VYN12dP"
            );

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");

            order = razorpay.orders.create(orderRequest);

            // ✅ Mark user as premium
            String email = (String) session.getAttribute("email");
            Users user = userService.getUser(email);
            user.setPremium(true);
            userService.updateUser(user);

            session.setAttribute("role", user.getRole()); // Store role in session

        } catch (RazorpayException e) {
            e.printStackTrace();
        }

        return order != null ? order.toString() : "{}";
    }

    // ✅ Step 3: Handle Razorpay "handler" -> verify payment
    @PostMapping("/verify")
    @ResponseBody
    public boolean verifyPayment(@RequestParam String orderId,
                                 @RequestParam String paymentId,
                                 @RequestParam String signature) {
        // ⚠️ Here, we can implement real signature verification using Razorpay SDK
        // For demo purpose, we assume success
        return true;
    }

    // ✅ Step 4: Redirect after payment is verified
    @GetMapping("/postPaymentSuccess")
    public String postPaymentSuccess(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        Users user = userService.getUser(email);

        if (user != null && user.isPremium()) {
            List<Song> songs = songService.fetchAllSongs();
            model.addAttribute("songs", songs);

            session.setAttribute("role", user.getRole()); // ensure role stored
            return "songDashboard"; // Your premium song page view
        } else {
            return "makePayment"; // Fallback
        }
    }
}
