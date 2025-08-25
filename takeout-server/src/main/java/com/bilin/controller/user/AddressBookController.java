package com.bilin.controller.user;

import com.bilin.context.BaseContext;
import com.bilin.entity.AddressBook;
import com.bilin.result.Result;
import com.bilin.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "Address Book API")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @ApiOperation("Query All Address of User")
    public Result<List<AddressBook>> list() {
        log.info("Query All Address of User");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    @PostMapping
    @ApiOperation("Add Address")
    public Result save(@RequestBody AddressBook addressBook) {
        log.info("Save Address");
        addressBookService.save(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("Search for Address by ID")
    public Result<AddressBook> getById(@PathVariable Long id) {
        log.info("Search for Address by ID");
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    @ApiOperation("Edit Address By ID")
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("Edit Address By ID");
        addressBookService.update(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("Set Default Address")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        log.info("Set Default Address");
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("Delete Address by ID")
    public Result deleteById(Long id) {
        log.info("Delete Address by ID");
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("default")
    @ApiOperation("Get Default ID")
    public Result<AddressBook> getDefault() {
        log.info("Get Default ID");
        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }

        return Result.error("No default address has been set");
    }

}
