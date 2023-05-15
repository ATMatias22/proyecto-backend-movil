package com.sensor.app.sensor_app_movil.repository;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInformativeMessageRepository extends JpaRepository<InformativeMessage, Long> {

    void deleteByFkDevice(Device fkDevice);

    List<InformativeMessage> findByFkDevice(Device fkDevice);


}
