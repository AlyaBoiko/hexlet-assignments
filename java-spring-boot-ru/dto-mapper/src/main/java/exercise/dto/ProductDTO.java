package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductDTO {
    private long id;
    private String title;
    private int price;
    private long vendorCode;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
