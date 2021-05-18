#r "nuget: FSToolkit.ErrorHandling" // F# 5 direct nuget references in scripts 
open FsToolkit.ErrorHandling
open System

type Customer =
  { Name : string
    Height : int }

let validateName name = async {
  if String.IsNullOrWhiteSpace name then return Error "Name can't be empty"
  else return Ok name
}

let validateHeight height = 
  if height > 0 then Ok height
  else Error "Everything has a height"

let validateCustomerForm name height =
  asyncResult {
    let! validName = validateName name
    and! validHeight = validateHeight height
    return { Name = validName; Height = validHeight }
  }

let sds<'t> (event: 't) = event