package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.Right;
import de.msg.training.donationmanager.repository.RightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightServiceImpl {

    @Autowired
    private final RightRepository rightRepository ;

    public RightServiceImpl(RightRepository rightRepository) {
        this.rightRepository = rightRepository;
    }

    public List<Right> findAllRights() {
        return rightRepository.findAll();
    }


    public Right saveRight(Right right) {
        return rightRepository.save(right);
    }

    public void deleteRightById(Long id) {
        rightRepository.deleteById(id);
    }
}
