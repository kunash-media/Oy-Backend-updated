package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.ShippingEntity;
import com.oy.oy_jewels.repository.ShippingRepository;
import com.oy.oy_jewels.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public ShippingEntity createShipping(ShippingEntity shipping) {
        return shippingRepository.save(shipping);
    }

    @Override
    public List<ShippingEntity> getAllShippings() {
        return shippingRepository.findAll();
    }

    @Override
    public Optional<ShippingEntity> getShippingById(Long id) {
        return shippingRepository.findById(id);
    }

    @Override
    public ShippingEntity updateShipping(Long id, ShippingEntity shipping) {
        Optional<ShippingEntity> existingShipping = shippingRepository.findById(id);
        if (existingShipping.isPresent()) {
            ShippingEntity entity = existingShipping.get();
            entity.setShippingTitle1(shipping.getShippingTitle1());
            entity.setShippingDescription1(shipping.getShippingDescription1());
            entity.setShippingTitle2(shipping.getShippingTitle2());
            entity.setShippingDescription2(shipping.getShippingDescription2());
            entity.setShippingTitle3(shipping.getShippingTitle3());
            entity.setShippingDescription3(shipping.getShippingDescription3());
            entity.setShippingTitle4(shipping.getShippingTitle4());
            entity.setShippingDescription4(shipping.getShippingDescription4());
            entity.setShippingTitle5(shipping.getShippingTitle5());
            entity.setShippingDescription5(shipping.getShippingDescription5());
            return shippingRepository.save(entity);
        }
        return null;
    }

    @Override
    public void deleteShipping(Long id) {
        shippingRepository.deleteById(id);
    }

    @Override
    public List<ShippingEntity> searchByTitle(String title) {
        return shippingRepository.findByShippingTitleContaining(title);
    }

    @Override
    public List<ShippingEntity> searchByDescription(String description) {
        return shippingRepository.findByShippingDescriptionContaining(description);
    }
}
