//package org.erp.service;
//
//import lombok.AllArgsConstructor;
//import org.erp.model.user.UserConnection;
//import org.erp.model.user.UserModel;
//import org.erp.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public UserModel signup(UserConnection userConnection) {
//        final UserModel userModel = UserModel.signUp(userConnection);
//        return userRepository.save(userModel);
//    }
//
//    public UserModel findByMember(UserConnection userConnection) {
//        final UserModel userModel = userRepository.findByUid(userConnection);
//
//        if (userModel == null) throw new RuntimeException();
//
//        return userModel;
//    }
//
//    public boolean isExistUser(UserConnection userConnection) {
//        final UserModel userModel = userRepository.findBySocial(userConnection);
//        return (userModel != null);
//    }
//
//}
