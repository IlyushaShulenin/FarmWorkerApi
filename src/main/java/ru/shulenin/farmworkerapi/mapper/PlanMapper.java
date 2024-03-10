package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Plan;
import ru.shulenin.farmworkerapi.dto.PlanReceiveDto;

@Mapper
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper( PlanMapper.class );

    public Plan planReceiveDtoToPlan(PlanReceiveDto plan);
}
