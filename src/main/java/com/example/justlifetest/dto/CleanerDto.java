package com.example.justlifetest.dto;

import com.example.justlifetest.dto.base.BaseApiDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CleanerDto extends BaseApiDTO {
    private String name;
    private String surname;
    private long vehicleId;
}
