package ubb.gamestore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO extends BaseDTO{
    private String name;
    private String description;
    private int price;
    private byte[] image;
}
